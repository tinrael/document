import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

/**
 * For implement this task focus on clear code, and make this solution as simple readable as possible
 * Don't worry about performance, concurrency, etc
 * You can use in Memory collection for sore data
 * <p>
 * Please, don't change class name, and signature for methods save, search, findById
 * Implementations should be in a single class
 * This class could be auto tested
 */
public class DocumentManager {
    private Set<Document> documents = new HashSet<>();

    /**
     * Implementation of this method should upsert the document to your storage
     * And generate unique id if it does not exist, don't change [created] field
     *
     * @param document - document content and author data
     * @return saved document
     */
    public Document save(@NonNull Document document) {
        if (document.id == null) {
            document.id = UUID.randomUUID().toString();
        }
        documents.add(document);
        return document;
    }

    /**
     * Implementation this method should find documents which match with request
     *
     * @param request - search request, each field could be null
     * @return list matched documents
     */
    public List<Document> search(@NonNull SearchRequest request) {
        return documents.stream()
                .filter(request::matches)
                .toList();
    }

    /**
     * Implementation this method should find document by id
     *
     * @param id - document id
     * @return optional document
     */
    public Optional<Document> findById(@NonNull String id) {
        return documents.stream()
                .filter(document -> id.equals(document.id))
                .findAny();
    }

    @Data
    @Builder
    public static class SearchRequest {
        private List<String> titlePrefixes;
        private List<String> containsContents;
        private List<String> authorIds;
        private Instant createdFrom;
        private Instant createdTo;

        public boolean matches(@NonNull Document document) {
            if ((titlePrefixes != null)
                    && titlePrefixes.stream().noneMatch(prefix -> document.title.regionMatches(true, 0, prefix, 0, prefix.length()))) {
                return false;
            }

            if ((containsContents != null)
                    && containsContents.stream().noneMatch(containsContent -> Pattern.compile(Pattern.quote(containsContent), Pattern.CASE_INSENSITIVE).matcher(document.content).find())) {
                return false;
            }

            if ((authorIds != null) && !authorIds.contains(document.author.id)) {
                return false;
            }

            if ((createdFrom != null) && document.created.isBefore(createdFrom)) {
                return false;
            }

            if ((createdTo != null) && document.created.isAfter(createdTo)) {
                return false;
            }

            return true;
        }
    }

    @Data
    @Builder
    public static class Document {
        private String id;
        private String title;
        private String content;
        private Author author;
        private Instant created;
    }

    @Data
    @Builder
    public static class Author {
        private String id;
        private String name;
    }
}