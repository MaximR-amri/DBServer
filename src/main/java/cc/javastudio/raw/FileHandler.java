package cc.javastudio.raw;

import java.io.*;

public class FileHandler implements Closeable{
    private final int INT_LENGTH = 4;
    private final int BYTE_LENGTH = 1;
    private RandomAccessFile dbFile;

    public FileHandler(String dbFileName) throws FileNotFoundException {
        dbFile = new RandomAccessFile(dbFileName, "rw");
    }

    public void add(String name,
                    int age,
                    String address,
                    String licencePlate,
                    String description) throws IOException {
        dbFile.seek(dbFile.length());
        // calculate the the total length of the record
        int recordLength =  INT_LENGTH + name.getBytes().length + //int.length telt aantal karakters. maar we moeten het aantal bytes hebben om problemen met ñ
                INT_LENGTH +
                INT_LENGTH + address.getBytes().length +
                INT_LENGTH + licencePlate.getBytes().length +
                INT_LENGTH + description.getBytes().length;

        dbFile.writeBoolean(false);
        dbFile.writeInt(recordLength);
        dbFile.writeInt(name.getBytes().length);
        dbFile.write(name.getBytes());
        dbFile.writeInt(age);
        dbFile.writeInt(address.getBytes().length);
        dbFile.write(address.getBytes());
        dbFile.writeInt(licencePlate.getBytes().length);
        dbFile.write(licencePlate.getBytes());
        dbFile.writeInt(description.getBytes().length);
        dbFile.write(description.getBytes());
    }

    public Person readRow(int rowNumber) throws IOException {

        byte[] rawData = readRawRow(0);
        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(rawData));

        Person person = new Person();
        int stringLength = stream.readInt();
        byte[] b = new byte[stringLength];
        stream.read(b);
        person.name = new String (b);

        person.age = stream.readInt();

        stringLength = stream.readInt();
        b = new byte[stringLength];
        stream.read(b);
        person.address = new String (b);

        stringLength = stream.readInt();
        b = new byte[stringLength];
        stream.read(b);
        person.LicencePlate = new String (b);

        stringLength = stream.readInt();
        b = new byte[stringLength];
        stream.read(b);
        person.description = new String (b);

        return person;
    }

    private byte[] readRawRow(int rowNumber) throws IOException {
        int pos = 0;
        dbFile.seek(pos);
        if (dbFile.readBoolean()) {
            return new byte[0];
        } else {
            int recordLength = dbFile.readInt();
            byte [] rawData = new byte[recordLength];
            dbFile.read(rawData);
            return rawData;
        }
    }

    public void close() throws IOException {
        dbFile.close();
    }
}
