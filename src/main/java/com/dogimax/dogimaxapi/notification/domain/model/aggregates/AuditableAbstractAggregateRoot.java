package com.dogimax.dogimaxapi.notification.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@MappedSuperclass // 1. Indica que no es una entidad, sino que sus campos van a las clases hijas
@EntityListeners(AuditingEntityListener.class) // 2. Habilita la auditoría automática
public abstract class AuditableAbstractAggregateRoot<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 3. Provee el ID a todas las entidades

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP) // 4. Se guarda automáticamente al crear
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // 5. Se actualiza automáticamente al modificar
    private Date updatedAt;

    public AuditableAbstractAggregateRoot() {
        super();
    }
}
