package app.entities.projections;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import app.entities.User;

@Projection(name = "default", types = { User.class })
public interface UserProjectionApp {
	
	@Value("#{target.getMembership()}")
	public HashMap<String,HashMap<String,String>> getMembership();

}
