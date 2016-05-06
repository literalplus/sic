package li.l1t.sic.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Base class for database entities with creation date and last update columns included.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-05-07
 */
public class BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private Date creationDate;
    @UpdateTimestamp
    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    public Date getCreationDate() {
        return creationDate;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
}
