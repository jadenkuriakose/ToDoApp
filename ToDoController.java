package demo.todo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class ToDoController {

    public static class Activity {
        private String name;
        private int priority;

        public Activity(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        public String getName() {
            return name;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }
    }

    private final List<Activity> items = new ArrayList<>();
    private int priority = 0;

    @GetMapping("/list")
    public String showToDoList(Model model) {
        model.addAttribute("toDo", this);
        return "todolist";
    }

    @PostMapping("/add")
    public String addActivity(@RequestParam String activityName, Model model) {
        Activity newActivity = new Activity(activityName, ++priority);
        items.add(newActivity);
        model.addAttribute("toDo", this);
        return "redirect:/todo/list";
    }

    @PostMapping("/remove")
    public String removeActivity(@RequestParam int priorityToRemove, Model model) {
        items.removeIf(activity -> activity.getPriority() == priorityToRemove);
        for (Activity remainingActivity : items) {
            if (remainingActivity.getPriority() > priorityToRemove) {
                remainingActivity.setPriority(remainingActivity.getPriority() - 1);
            }
        }
        
        if (!items.isEmpty()) {
            --priority;
        } else {
            priority = 0; 
        }
    

        model.addAttribute("toDo", this);
        return "redirect:/todo/list";
    }
    

    public List<Activity> getItems() {
        return items;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(Activity activity, int newPriority) {
        activity.setPriority(newPriority);
    }
}
