package nsu.akatov.third_task;

import nsu.akatov.third_task.dto.NodeDto;
import nsu.akatov.third_task.dto.TagDto;
import nsu.akatov.third_task.entities.NodeEntity;
import nsu.akatov.third_task.entities.TagEntity;
import nsu.akatov.third_task.generated.Node;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {
    public static NodeEntity convertNodeToNodeEntity(Node node) {
        List<TagEntity> tags = node.getTag()
                .stream()
                .map(tag -> convertTagToTagEntity(tag, node.getId()))
                .collect(Collectors.toList());

        return new NodeEntity(
                tags,
                node.getId(),
                node.getVersion(),
                node.getUid(),
                node.getUser(),
                node.getChangeset(),
                node.getLon(),
                node.getLat()
        );
    }

    private static TagEntity convertTagToTagEntity(Node.Tag tag, int nodeId) {
        return new TagEntity(
                tag.getK(),
                tag.getV(),
                nodeId
        );
    }
    private static TagEntity convertTagToTagEntity(String key, String value, int nodeId) {
        return new TagEntity(
                key,
                value,
                nodeId
        );
    }

    public static NodeEntity convertNodeDtoToNodeEntity(NodeDto nodeDto){
        List<TagEntity> tags = nodeDto.getTag()
                .stream()
                .map(tag -> convertTagToTagEntity(
                        tag.getK(),
                        tag.getV(),
                        nodeDto.getId()))
                .collect(Collectors.toList());
        return new NodeEntity(
                tags,
                nodeDto.getId(),
                nodeDto.getVersion(),
                nodeDto.getUid(),
                nodeDto.getUser(),
                nodeDto.getChangeset(),
                nodeDto.getLon(),
                nodeDto.getLat()
        );
    }

    public static NodeDto convertNodeEntityToNodeDto(NodeEntity nodeEntity){
        List<TagDto> tags = nodeEntity.getTag()
                .stream()
                .map(tag -> new TagDto(tag.getK(), tag.getV()))
                .collect(Collectors.toList());
        return new NodeDto(
                tags,
                nodeEntity.getId(),
                nodeEntity.getVersion(),
                nodeEntity.getUid(),
                nodeEntity.getUser(),
                nodeEntity.getChangeset(),
                nodeEntity.getLon(),
                nodeEntity.getLat()
        );
    }
}
