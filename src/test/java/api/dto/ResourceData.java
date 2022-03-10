package api.dto;

import lombok.Data;

@Data
public class ResourceData {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;
}
