package api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResourceCollection {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<ResourceData> data;
    private Support support;
}
