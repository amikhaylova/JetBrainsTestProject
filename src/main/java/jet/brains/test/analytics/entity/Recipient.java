package jet.brains.test.analytics.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "recipient")
public class Recipient {
    @Column(name = "address")
    @Id
    private String address;
}
