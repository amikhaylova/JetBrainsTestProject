package jet.brains.test.analytics.entity;

import jet.brains.test.analytics.enums.DataType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "variables")
public class Variable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DataType type;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;
}
