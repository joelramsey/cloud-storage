package com.ramsey.cloudstorage.controller;

import com.ramsey.cloudstorage.model.CredentialForm;
import com.ramsey.cloudstorage.model.FileForm;
import com.ramsey.cloudstorage.model.NoteForm;
import com.ramsey.cloudstorage.services.CredentialService;
import com.ramsey.cloudstorage.services.FileService;
import com.ramsey.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private CredentialService credentialService;
    private FileService fileService;

    public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getCredentialPage(CredentialForm credentialForm, Model model) {
        model.addAttribute("credentials", this.credentialService.getCredentials());
        return "credential";
    }

    @GetMapping
    public String getNotePage(NoteForm noteForm, Model model) {
        model.addAttribute("notes", this.noteService.getNotes());
        return "note";
    }

    @GetMapping
    public String getFilePage(FileForm fileForm, Model model) {
        model.addAttribute("files", this.fileService.getFiles());
        return "file";
    }

    @PostMapping
    public String postCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        credentialForm.setUsername(authentication.getName());
        this.credentialService.addCredential(credentialForm);
        credentialForm.setCredentialText("");
        model.addAttribute("credentials", this.credentialService.getCredentials());
        return "credential";
    }

    @PostMapping
    public String postNote(Authentication authentication, NoteForm noteForm, Model model) {
        noteForm.setUsername(authentication.getName());
        this.noteService.addNote(noteForm);
        noteForm.setNoteText("");
        model.addAttribute("notes", this.noteService.getNotes());
        return "note";
    }

    @PostMapping
    public String postFile(Authentication authentication, FileForm fileForm, Model model) {
        fileForm.setUsername(authentication.getName());
        this.fileService.addFile(fileForm);
        fileForm.setFileText("");
        model.addAttribute("files", this.fileService.getFiles());
        return "file";
    }
}