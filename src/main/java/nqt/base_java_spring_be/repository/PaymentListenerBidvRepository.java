package nqt.base_java_spring_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nqt.base_java_spring_be.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentListenerBidvRepository extends JpaRepository<User, UUID> {

}
