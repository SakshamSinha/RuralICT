package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.message.TextMessage;

public interface TextMessageRepository extends JpaRepository<TextMessage, Integer> 
{

}