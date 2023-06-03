package pl.ersms.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "restaurant_id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long restaurantId;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuItem> menuItems = new LinkedHashSet<>();

    @Column(name = "name", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String name;

    @Column(name = "user_id", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String userId;

    @Column(name = "address_city", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String addressCity;

    @Column(name = "address_street", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String addressStreet;

    @Column(name = "address_building_number", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String addressBuildingNumber;

    @Column(name = "address_flat_number", length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String addressFlatNumber;

    @Column(name = "phone_number", length = 50)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String phoneNumber;

    @Column(name = "is_approved", columnDefinition = "BIT", nullable = false)
    @JdbcTypeCode(SqlTypes.BIT)
    private Boolean isApproved;

}
