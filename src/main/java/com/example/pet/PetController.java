package com.example.pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.pet.DTO.*;
import com.example.pet.Data.RegistroPerdaData;
import com.example.pet.Services.*;

@Controller
@CrossOrigin(origins = "*")
public class PetController {

	@Autowired
    private PetDataService petDataService;

    private final String UPLOAD_DIR = System.getProperty("user.dir")
    										.replace("/target", "") + "/uploads/";

    @GetMapping("/")
    public String index(Model model) {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();
        
        model.addAttribute("especies", petDataService.buscarTodasAsEspecies());
        
        try (var stream = Files.list(Paths.get(UPLOAD_DIR))) {
            List<String> arquivos = stream
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            model.addAttribute("arquivos", arquivos);          
            model.addAttribute("dadosPerdas", petDataService.buscarTodasAsPerdas());
            
        } catch (Exception e) {
            model.addAttribute("arquivos", Collections.emptyList());
            model.addAttribute("dadosPerdas", Collections.emptyList());
        }
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute RegistroPerdaDTO dto,
                         @RequestParam("imagem") MultipartFile imagem,
                         Model model) throws IOException {

        if (!imagem.isEmpty()) {
        	
           	RegistroPerdaData novaPerda = petDataService.registrarPerda(dto);
        	
            // Pega a extensão original (ex: .jpg)
            String originalName = imagem.getOriginalFilename();
            String extensao = originalName.substring(originalName.lastIndexOf("."));

            // Novo nome: [nome]_[data]_[local].ext
            String novoNome = String.format("%s_%s%s", 
					            				dto.getNome().replace(' ', '-'),
					            				novaPerda.getPerdaId(), extensao);

            Path path = Paths.get(UPLOAD_DIR + novoNome);
            Files.write(path, imagem.getBytes());

            model.addAttribute("mensagem", "Registro de perda '" + novoNome + "' realizado com sucesso!");
        }

        return index(model);
    }
    
    @GetMapping("/api/pets")
    @ResponseBody // Retorna JSON em vez de HTML
    public List<String> buscarPets(@RequestParam(value = "termo", required = false) String termo) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) return Collections.emptyList();

        try (var stream = Files.list(Paths.get(UPLOAD_DIR))) {
            return stream
                    .map(path -> path.getFileName().toString())
                    // Filtra pelo termo (ignora maiúsculas/minúsculas)
                    .filter(nome -> termo == null || termo.isEmpty() || 
                            nome.toLowerCase().contains(termo.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/api/perdas/termos")
    @ResponseBody // Retorna JSON em vez de HTML
    public List<RegistroPerdaDTO> buscar(@RequestParam("termo") String termo) {
    	
        // 2. Buscar no banco
        List<RegistroPerdaDTO> resultados = petDataService.buscarPerdasPorTermos(termo);

        return resultados;
    }

    @GetMapping("/api/perda/find")
    @ResponseBody // Retorna JSON em vez de HTML
    public RegistroPerdaDTO consultar(@RequestParam("id") int id) throws IOException {
    	
    	var retornoDTO = petDataService.buscarPerdaPorId(id);
    	
    	if (retornoDTO != null)
    	{
	        File dir = new File(UPLOAD_DIR);
	        if (!dir.exists()) return new RegistroPerdaDTO();

	        try (var stream = Files.list(Paths.get(UPLOAD_DIR))) {
	            var img = stream
	                    .map(path -> path.getFileName().toString())
	                    .filter(nome -> nome.contains(String.valueOf(id)))
	                    .findFirst();
	            if (!img.isEmpty())
	            	retornoDTO.setUrlImagem(img.toString());
	        }  	

	        return retornoDTO;
    	}
    	else
    		return new RegistroPerdaDTO();

    }


}
