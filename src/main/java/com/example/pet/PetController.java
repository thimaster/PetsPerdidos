package com.example.pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.pet.DTO.*;
import com.example.pet.Entities.*;
import com.example.pet.Factories.PetFactory;

@Controller
@CrossOrigin(origins = "*")
public class PetController {

    private final String UPLOAD_DIR = System.getProperty("user.dir")
    										.replace("/target", "") + "/uploads/";

    @GetMapping("/")
    public String index(Model model) {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        try (var stream = Files.list(Paths.get(UPLOAD_DIR))) {
            List<String> arquivos = stream
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            model.addAttribute("arquivos", arquivos);
        } catch (Exception e) {
            model.addAttribute("arquivos", Collections.emptyList());
        }
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute RegistroPerdaDTO dto,
                         @RequestParam("imagem") MultipartFile imagem,
                         Model model) throws IOException {

        if (!imagem.isEmpty()) {
        	
        	  	// 1. A Factory resolve a complexidade da herança
            Pet petReal = PetFactory.criarPet(dto);

            // 2. Cria a associação (Entity)
            RegistroPerda registro = new RegistroPerda(
                petReal, 
                dto.getLocal(), 
                LocalDate.parse(dto.getDataOcorrido()), 
                dto.getDescricao()
            );
            
            // Pega a extensão original (ex: .jpg)
            String originalName = imagem.getOriginalFilename();
            String extensao = originalName.substring(originalName.lastIndexOf("."));

            // Novo nome: [nome]_[data]_[local].ext
            String novoNome = String.format("%s_%s_%s_%s%s", 
					            				dto.getTipoPet(),
					            				petReal.nomeFormatado(), 
					            				registro.dataFormatada(), 
					            				registro.localFormatado(), extensao);

            Path path = Paths.get(UPLOAD_DIR + novoNome);
            Files.write(path, imagem.getBytes());

            model.addAttribute("mensagem", "Upload de '" + novoNome + "' realizado com sucesso!");
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

}
