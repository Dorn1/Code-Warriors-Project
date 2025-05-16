package pl.summarizer.codewarriorsproject.User;

import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Exception.AlreadyExistsException;
import pl.summarizer.codewarriorsproject.Exception.DoesntExistException;


@Service
public class UserService {
    UserRepository userRepository;
    public void addUser(String username, String password) throws AlreadyExistsException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if(!userRepository.findByUsername(username).isEmpty()){
            throw new AlreadyExistsException("Username " + username + " already exists");
        }
        userRepository.save(user);
    }
    public boolean login(String username, String password) throws DoesntExistException {
        if(userRepository.findByUsername(username).isEmpty()){
            throw new DoesntExistException("Username " + username + " does not exist");
        }
        return userRepository.findByUsername(username).getFirst().getPassword().equals(password);
    }
    public Long getUserId(String username) {
        return userRepository.findByUsername(username).getFirst().getId();
    }
}
