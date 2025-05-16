package pl.summarizer.codewarriorsproject.User;

import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Exception.AlreadyExistsException;
import pl.summarizer.codewarriorsproject.Exception.DoesntExistException;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<User> users = userRepository.findByUsername(username);
        if(!userRepository.findByUsername(username).isEmpty()){
            throw new AlreadyExistsException("Username " + username + " already exists");
        }
        userRepository.save(user);
    }
    public boolean login(String username, String password) {
        if(userRepository.findByUsername(username).isEmpty()){
            throw new DoesntExistException("Username " + username + " does not exist");
        }
        return userRepository.findByUsername(username).getFirst().getPassword().equals(password);
    }
}
