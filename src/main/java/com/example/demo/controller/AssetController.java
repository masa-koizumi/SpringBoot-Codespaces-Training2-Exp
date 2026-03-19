@Controller
@RequestMapping("/assets")
public class AssetController {

    private final AssetRepository repo;

    public AssetController(AssetRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assets", repo.findAll());
        return "assets";
    }

    @PostMapping
    public String create(String name) {
        Asset a = new Asset();
        a.setName(name);
        a.setStatus("AVAILABLE");
        repo.save(a);
        return "redirect:/assets";
    }
}
