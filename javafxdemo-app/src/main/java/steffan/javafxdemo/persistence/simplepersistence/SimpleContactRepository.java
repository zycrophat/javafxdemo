package steffan.javafxdemo.persistence.simplepersistence;

import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SimpleContactRepository implements Repository<Contact> {

    private Map<Long, Contact> contactMap = new TreeMap<>();
    private File dbFile = new File("contacts");

    @Override
    public Contact find(long key) throws PersistenceException {
        try {
            dbFile.createNewFile();
            try(LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(dbFile)))) {
                return reader
                        .lines()
                        .map(this::mapLineToContact)
                        .filter(c -> c.getId() == key)
                        .findFirst().orElse(null);
            }
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Contact> find() throws PersistenceException {
        try {
            dbFile.createNewFile();
            try(LineNumberReader reader = createLineNumberedReader()) {

                return new ArrayList<>(reader
                        .lines()
                        .map(this::mapLineToContact)
                        .collect(Collectors.toMap(Contact::getId, c -> c)).values());
            }
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    private Map<Long, Contact> loadContacts() throws IOException {
        dbFile.createNewFile();
        try(LineNumberReader reader = createLineNumberedReader()) {

            return reader
                    .lines()
                    .map(this::mapLineToContact)
                    .collect(Collectors.toMap(Contact::getId, c -> c));
        }
    }

    private LineNumberReader createLineNumberedReader() throws FileNotFoundException {
        return new LineNumberReader(new InputStreamReader(new FileInputStream(dbFile), StandardCharsets.UTF_8));
    }

    private Contact mapLineToContact(String line) {
        String[] contactAttributes = line.split(",");

        long key = Long.parseLong(contactAttributes[0]);
        String firstName = contactAttributes[1];
        String lastName = contactAttributes[2];

        return new Contact(key, firstName, lastName);
    }

    @Override
    public void store(Contact object) throws PersistenceException {
        store(List.of(object));
    }

    @Override
    public void store(List<Contact> objects) throws PersistenceException {
        try {
            var contacts = loadContacts();
            objects.forEach(contact -> contacts.put(contact.getId(), contact));
            dbFile.createNewFile();
            try(BufferedWriter writer = createBufferedWriter()) {

                for (var c : contacts.values()) {
                    writer.write(String.join(",", Long.toString(c.getId()), c.getFirstName(), c.getLastName()));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    private BufferedWriter createBufferedWriter() throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dbFile), StandardCharsets.UTF_8));
    }

    @Override
    public void delete(Contact object) throws PersistenceException {
        contactMap.remove(object.getId());
    }

    @Override
    public void delete(List<Contact> objects) throws PersistenceException {
        try {
            var contacts = loadContacts();
            objects.stream().map(Contact::getId).forEach(contacts::remove);
            dbFile.createNewFile();
            try(BufferedWriter writer = createBufferedWriter()) {

                for (var c : contacts.values()) {
                    writer.write(String.join(",", Long.toString(c.getId()), c.getFirstName(), c.getLastName()));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

}
