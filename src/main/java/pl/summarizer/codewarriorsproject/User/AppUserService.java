package pl.summarizer.codewarriorsproject.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Exception.AlreadyExistsException;
import pl.summarizer.codewarriorsproject.Exception.DoesntExistException;


@Service
public class AppUserService {
    @Autowired
    public AppUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    AppUserRepository userRepository;
    public void addUser(String username, String password) throws AlreadyExistsException {
        AppUser user = new AppUser();
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
    public AppUser getUser(Long id) throws DoesntExistException {
        if (!userRepository.existsById(id)) {
            throw new DoesntExistException("User doesn't exist");
        }
        return userRepository.findById(id).get();
    }
}
