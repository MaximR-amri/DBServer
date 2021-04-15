package cc.javastudio.raw;

import java.io.*;

public class FileHandler implements Closeable {
    private RandomAccessFile dbFile;
    private final int INT_LENGTH = 4;
    private final int BYTE_LENGTH = 1;

    public FileHandler(String dbFileName) throws FileNotFoundException {
        dbFile = new RandomAccessFile(dbFileName, "rw");
    }

    public void add(String name, int age,
                    String address,
                    String licensePlate,
                    String description) throws IOException {
        dbFile.seek(dbFile.length());
        //calculate the total length of the record
        //eerst bijschrijven hoe lang hij is (integer)
        int recordlength = INT_LENGTH + name.length() +
                            INT_LENGTH +
                            INT_LENGTH + address.length() +
                            INT_LENGTH + licensePlate.length() +
                            INT_LENGTH + description.length();
        dbFile.writeBoolean(false);
        dbFile.writeInt(recordlength);
        dbFile.writeInt(name.length());
        dbFile.write(name.getBytes());
        dbFile.write(age);
        dbFile.writeInt(address.length());
        dbFile.write(address.getBytes());
        dbFile.writeInt(licensePlate.length());
        dbFile.write(licensePlate.getBytes());
        dbFile.writeInt(description.length());
        dbFile.write(description.getBytes());
    }

    public Person readRow(int rowNumber) throws IOException {
        byte[] rawData = readRawRow(rowNumber);
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
        person.licensePlate = new String (b);

        stringLength = stream.readInt();
        b = new byte[stringLength];
        stream.read(b);
        person.description = new String (b);

        return person;
    }

    private byte[] readRawRow(int rowNumber) throws IOException {
        int pos = 0;
        byte[] rawData;

        dbFile.seek(pos);
        if(dbFile.readBoolean()){
            return new byte[0];
        } else {

            int recordLength = dbFile.readInt();
            rawData = new byte[recordLength];
            dbFile.read(rawData);
            return rawData;
        }


    }

    public void close() throws IOException {
        dbFile.close();
    }
}
