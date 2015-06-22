package app.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.message.VoiceMessage;

public interface VoiceMessageRepository extends JpaRepository< VoiceMessage, Integer> 
{

}