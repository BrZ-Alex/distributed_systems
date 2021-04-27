package entities;

import generated.Node;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class NodeEntity {
    private List<TagEntity> tag;
    private int id;
    private int version;
    private int uid;
    private String user;
    private int changeset;
    private double lat;
    private double lon;

    public NodeEntity(Node node) {
        tag = node.getTag().stream()
                .map(tag -> new TagEntity(node.getId(), tag))
                .collect(Collectors.toList());
        id = node.getId();
        version = node.getVersion();
        uid = node.getUid();
        user = node.getUser();
        changeset = node.getChangeset();
        lat = node.getLat();
        lon = node.getLon();
    }
}
