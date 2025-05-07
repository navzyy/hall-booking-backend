@Controller
public class HallController {

    @Autowired
    private HallRepository hallRepository;

    @GetMapping("/index")
    public String showHalls(Model model) {
        List<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        return "index"; // 👈 this must match the HTML file name exactly
    }
}
