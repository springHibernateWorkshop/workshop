package spring.workshop.expenses.entities;

import java.sql.Timestamp;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity {

    @Version
    private Timestamp version;

    protected Timestamp getVersion() {
        return version;
    }

    protected void setVersion(Timestamp version) {
        this.version = version;
    }

}
