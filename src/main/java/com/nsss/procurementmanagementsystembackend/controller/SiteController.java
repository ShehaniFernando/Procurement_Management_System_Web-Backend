package com.nsss.procurementmanagementsystembackend.controller;

import com.nsss.procurementmanagementsystembackend.constant.Constants;
import com.nsss.procurementmanagementsystembackend.model.Site;
import com.nsss.procurementmanagementsystembackend.repository.SiteRepository;
import com.nsss.procurementmanagementsystembackend.response.MessageResponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Constants.RequestMapping.REQUEST_MAPPING)
public class SiteController {

    public  static final Logger logger = LoggerFactory.getLogger(SiteController.class);
    @Autowired
    SiteRepository siteRepository;

    /**
     * This method loads data into ArrayList of Site objects
     *
     * @throws NumberFormatException
     *                 -Thrown to indicate that the application has attempted to
     *                 convert a string to one of the numeric types
     */

    @GetMapping(Constants.RequestMapping.SITES)
    public ResponseEntity<List<Site>> getAllSites(@RequestParam(required = false) String siteName) {
        try {
            List<Site> sites = new ArrayList<Site>();

            if (siteName == null)
                siteRepository.findAll().forEach(sites::add);
            else
                siteRepository.findAllByName(siteName).forEach(sites::add);

            if (sites.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sites, HttpStatus.OK);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(Constants.RequestMapping.SITES)
    public ResponseEntity<?> addSite(@Valid @RequestBody Site site){
        siteRepository.save(site);

        return ResponseEntity.ok(new MessageResponse(Constants.Message.SITE_CREATED_SUCCESSFULLY));
    }
}