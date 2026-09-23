package vom.mycompany.mywebapp;

import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles browser requests for the User Management UI.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** Redirect the root URL to the user list. */
    @GetMapping({"", "/"})
    public String home() {
        return "redirect:/users/list";
    }

    /** Show all users. */
    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    /** Show the form for creating a new user. */
    @GetMapping("/showForm")
    public String showUserForm(User user) {
        return "add-user";
    }

    /** Create a new user. */
    @PostMapping("/add")
    public String addUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userRepository.save(user);
        return "redirect:/users/list";
    }

    /** Show the edit form for an existing user. */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));

        model.addAttribute("user", user);
        return "update-user";
    }

    /** Update an existing user. */
    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable Long id,
            @Valid User user,
            BindingResult result) {

        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        // Keep the original identifier and persist the edited values.
        user.setId(id);
        userRepository.save(user);

        return "redirect:/users/list";
    }

    /** Delete a user. Kept as GET to stay close to the linked tutorial. */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));

        userRepository.delete(user);
        return "redirect:/users/list";
    }
}
