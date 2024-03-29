package steffan.javafxdemo.csvpersistence.simplepersistence;

import steffan.javafxdemo.core.models.domainmodel.Contact;
import steffan.javafxdemo.core.persistence.api.PersistenceException;
import steffan.javafxdemo.core.persistence.api.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleContactRepository implements Repository<Contact> {

    private File dbFile = new File("contacts");

    @Override
    public Optional<Contact> find(long key) throws PersistenceException {
        try {
            dbFile.createNewFile();
            try(LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(dbFile)))) {
                return reader
                        .lines()
                        .map(this::mapLineToContact)
                        .filter(c -> c.getId() == key)
                        .findFirst();
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
        String[] contactAttributes = line.split(",", 3);

        long key = getOptionalField(contactAttributes, 0).map(Long::parseLong).orElse(-1L);
        String firstName = getOptionalField(contactAttributes, 1).orElse("");
        String lastName = getOptionalField(contactAttributes, 2).orElse("");

        return new Contact(key, firstName, lastName);
    }

    private <T> Optional<T> getOptionalField(T[] contactAttributes, int i) {
        return contactAttributes.length > i ? Optional.of(contactAttributes[i]) : Optional.empty();
    }

    @Override
    public void store(Contact contact) throws PersistenceException {
        try {
            var contacts = loadContacts();

            if (isNewContact(contact)) {
                var id = generateId(contacts);
                contact.setId(id);
            }
            contacts.put(contact.getId(), contact);
            mkCleanContactsFile();
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

    private void mkCleanContactsFile() throws IOException {
        Files.delete(dbFile.toPath());
        Files.createFile(dbFile.toPath());
    }

    private long generateId(Map<Long, Contact> contactMap) {
        return contactMap.keySet().stream().max(Long::compareTo).map(id -> id + 1L).orElse(1L);
    }

    private boolean isNewContact(Contact contact) {
        return contact.getId() == -1L;
    }

    private BufferedWriter createBufferedWriter() throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dbFile), StandardCharsets.UTF_8));
    }

    @Override
    public void delete(Contact object) throws PersistenceException {
        try {
            var contacts = loadContacts();
            contacts.remove(object.getId());
            mkCleanContactsFile();
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
