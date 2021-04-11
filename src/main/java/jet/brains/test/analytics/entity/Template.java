package jet.brains.test.analytics.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "templates")
public class Template {
    @Column(name = "template_id")
    @Id
    private String templateId;
    private String template;
    @ManyToMany
    @JoinTable(name = "template_recipients",
            joinColumns = @JoinColumn(name = "template_id", referencedColumnName = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "address", referencedColumnName = "address"))
    private List<Recipient> recipients;
}
