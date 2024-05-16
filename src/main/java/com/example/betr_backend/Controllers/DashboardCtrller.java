package com.example.betr_backend.Controllers;

import com.example.betr_backend.Models.BudgetModels.IdMonthYearModel;
import com.example.betr_backend.Models.DashboardModel.DashboardModel;
import com.example.betr_backend.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin
public class DashboardCtrller {
    @Autowired
    private DashboardService ds;

    @GetMapping("/getDashboardDetails")
    public ResponseEntity<DashboardModel> getDashboardDetails(@RequestBody IdMonthYearModel imy){
        DashboardModel dbm = ds.DashboardDetails(imy);
        return ResponseEntity.ok(dbm);
    }
}