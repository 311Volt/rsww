package com.yetistudios.rsww.rswwhotel.restadmin;

import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import com.yetistudios.rsww.rswwhotel.restadmin.service.AdminImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@Profile("!test")
public class AdminImportController {

    @Autowired
    private AdminImportService adminImportService;

    @CrossOrigin
    @PostMapping("/import-hotel")
    public void importHotels(@RequestBody Hotel hotel) {
        adminImportService.importHotel(hotel);
    }
}
