package nsu.akatov.third_task.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.akatov.third_task.generated.Node;

import javax.persistence.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nodes")
public class NodeEntity {
    @OneToMany(mappedBy = "node", cascade = CascadeType.ALL)
    private List<TagEntity> tag;
    @Id
    private int id;
    @Column
    private int version;
    @Column
    private int uid;
    @Column(name = "users")
    private String user;
    @Column
    private int changeset;
    @Column
    private double lat;
    @Column
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
