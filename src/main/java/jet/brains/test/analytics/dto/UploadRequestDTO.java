package jet.brains.test.analytics.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UploadRequestDTO implements Serializable {
    @NotNull
    @NotEmpty
    private String templateId;
    @NotNull
    @NotEmpty
    private String template;
    @NotNull
    @NotEmpty
    private List<String> recipients;
    //field for additional task with variables' values validation
    private List<Map<String, String>> variables;
}
