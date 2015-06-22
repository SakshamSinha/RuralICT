package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.message.BinaryMessage;

public interface BinaryMessageRepository extends JpaRepository<BinaryMessage, Integer> 
{

}