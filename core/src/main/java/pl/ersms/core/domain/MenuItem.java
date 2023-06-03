package pl.ersms.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "name", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String name;

    @Column(name = "description", nullable = false, length = 4096)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    @JdbcTypeCode(SqlTypes.DECIMAL)
    private BigDecimal price;

    @Column(name = "pending_user_id", length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String pendingUserId;

    @Column(name = "accepted_user_id", length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String acceptedUserId;

    @Column(name = "accepted_date")
    private Timestamp acceptedDate;

    @Column(name = "collected_date")
    private Timestamp collectedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuItem menuItem = (MenuItem) o;
        return getId() != null && Objects.equals(getId(), menuItem.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum State {
        AVAILABLE,
        PENDING,
        RESERVED,
        FINISHED
    }
}