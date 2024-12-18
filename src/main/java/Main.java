import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DocumentManager documentManager = new DocumentManager();

        documentManager.save(new DocumentManager.Document(
                "15",
                "Modern Operating Systems",
                "Modern Operating Systems incorporates the latest developments and technologies in operating systems (OS) technologies.",
                new DocumentManager.Author("20", "Andrew S. Tanenbaum"),
                Instant.parse("2022-05-02T10:00:00Z")
        ));
        documentManager.save(new DocumentManager.Document(
                "25",
                "Introduction to Algorithms",
                "Some books on algorithms are rigorous but incomplete; others cover masses of material but lack rigor. Introduction to Algorithms uniquely combines rigor and comprehensiveness.",
                new DocumentManager.Author("45", "Thomas H. Cormen"),
                Instant.parse("2022-04-05T08:00:00Z")
        ));
        documentManager.save(new DocumentManager.Document(
                "70",
                "Design Patterns: Elements of Reusable Object-Oriented Software",
                "Capturing a wealth of experience about the design of object-oriented software, four top-notch designers present a catalog of simple and succinct solutions to commonly occurring design problems.",
                new DocumentManager.Author("15", "Erich Gamma"),
                Instant.parse("1994-01-01T09:00:00Z")
        ));
        documentManager.save(DocumentManager.Document.builder()
                .title("Effective Java")
                .content("In this new edition of Effective Java, Bloch explores new design patterns and language idioms that have been introduced since the second edition was released in 2008 shortly after Java SE6, including Lambda, streams, generics and collections, as well as selected Java 9 features.")
                .build());

        DocumentManager.SearchRequest searchRequest = DocumentManager.SearchRequest.builder()
                .titlePrefixes(List.of("modern", "effective"))
                .containsContents(List.of("OS", "Java"))
                .build();

        List<DocumentManager.Document> documents = documentManager.search(searchRequest);
        if (!documents.isEmpty()) {
            for (DocumentManager.Document document : documents) {
                System.out.println(document.getId() + " " + document.getTitle());
            }
        } else {
            System.out.println("Not found.");
        }
    }
}
