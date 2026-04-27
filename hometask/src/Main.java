import hometask1.SnapshotStringBuilder;

public class Main {
    public static void main(String[] args) {

        SnapshotStringBuilder ssb = new SnapshotStringBuilder();
        ssb.append("Snapshot ").append("is").append(" a ");
        System.out.println(ssb);

        ssb.snapshot();

        ssb.append("bad");
        System.out.println(ssb);

        if(ssb.undo()) {
            System.out.println(ssb);
        }

        if(ssb.undo()) {
            System.out.println(ssb);
        }
        else {
            System.out.println(ssb.append("good idea"));
        }
    }
}