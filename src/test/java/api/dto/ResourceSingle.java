package api.dto;

import lombok.Data;

@Data
public class ResourceSingle {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private ResourceData data;
    private Support support;
}
