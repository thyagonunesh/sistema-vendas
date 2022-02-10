package com.estudos.vendas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estudos.vendas.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query("select p from Pedido p left join fetch p.items where p.id = :id")
	Optional<Pedido> findByIdFetchItems(@Param("id") Long id);
	
}
