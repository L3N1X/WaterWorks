package hr.algebra.waterworks.controllers.rest;

import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api")
public class ApiController {
    private WaterWorksService waterWorksService;

    @GetMapping("image/{itemId}")
    public String getBase64ImageForItem(@PathVariable int itemId){
        ItemDto item = waterWorksService.getItem(itemId);
        if(item == null)
            return null;
        return item.base64content();
    }
}
