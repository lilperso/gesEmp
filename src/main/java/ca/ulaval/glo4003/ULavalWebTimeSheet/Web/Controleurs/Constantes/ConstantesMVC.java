package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes;

public class ConstantesMVC
{
    //Vues
    public static final String VUE_LISTE_EMPLOYE = "listeDesEmployes";
    public static final String VUE_ASSIGNATION_PROJET = "assignationProjet";
    public static final String VUE_ERREUR_EXCEPTION = "erreurs/erreur_exception";
    public static final String VUE_LISTE_APPROBATION = "listeDesDemandePourUnEmploye";
    
    public static final String VUE_ACCUEIL_UTILISATEUR = "accueilUtilisateur";
    public static final String VUE_CREER_EMPLOYE = "creerEmploye";
    public static final String VUE_CONNEXION = "connexion";
    
    public static final String VUE_CREER_PROJET = "creerNouveauProjet";
    public static final String VUE_CREER_TACHE = "creerNouvelleTache";
    public static final String VUE_ENTRER_HEURE = "entrerHeuresEmploye";
    
    public static final String VUE_GERER_PROJET = "gererProjets";
    public static final String VUE_MODIFIER_TACHE = "modifierTache";
    
    public static final String VUE_MODIFIER_PROJET = "modifierProjet";
    
    
    
    //Actions
    public static final String ACTION_ASSIGNER_PROJET = "/assignerProjet-action";
    public static final String ACTION_APPROUVER_DEPENSE = "/approuver-demande-depense";
    public static final String ACTION_APPROUVER_DEPLACEMENT = "/approuver-demande-deplacement";
    
    public static final String ACTION_CREER_EMPLOYE = "/creerEmploye-action";
    public static final String ACTION_MODIFIER_EMPLOYE = "/modifier-employe-action";
    public static final String ACTION_LOGIN = "/login-action";
    
    public static final String ACTION_PERIODE_SUIVANTE = "/periode-suivante-action";
    public static final String ACTION_PERIODE_PRECEDENTE = "/periode-precedente-action";
    public static final String ACTION_ENTRER_HEURES = "/entrer-mes-heures-employe-action";
    
    public static final String ACTION_SUPPRIMER_PROJET = "supprimerProjet";
    public static final String ACTION_SUPPRIMER_TACHE = "supprimerTache";
    
    
    //RequestMapping
    public static final String AFFICHER_LISTE_EMPLOYE_ET_PROJET = "/afficherListeEmployeEtListeProjet";
    public static final String AFFICHER_LISTE_DEMANDES_APPROBATION = "/listes-demandes-approbations-pour-employe";
    public static final String AFFICHER_ACCUEIL = "/";
    public static final String AFFICHER_ENTRER_HEURES = "/entrer-mes-heures-employe";
    
    public static final String AFFICHER_ASSIGNATION = "/consulterAssignations";
    public static final String AFFICHER_LISTE_EMPLOYE = "/redirect-listeEmploye";
    public static final String AFFICHER_MODIFIER_EMPLOYE = "/redirect-modifierEmploye";
    
    public static final String AFFICHER_CREER_PROJET = "creerNouveauProjet";
    public static final String AFFICHER_GERER_PROJET = "gererProjets";
    public static final String AFFICHER_CREER_TACHE = "creerNouvelleTache";
    public static final String AFFICHER_LOGIN = "/login";
    
    public static final String AFFICHER_ENTRER_TEMPS = "/redirect-entrerTemps";
    
    public static final String AFFICHER_MODIFIER_TACHE = "modifierTache";
    public static final String AFFICHER_MODIFIER_PROJET = "modifierProjet";
    //Redirect
    public static final String REDIRECT_LISTEEMPLOYE = "redirect:/redirect-listeEmploye";
    
    
    //Autres constantes
    public static final String LISTE_PROJET_DTO = "listeProjetDTO";
    public static final String LISTE_COURRIEL_EMPLOYE_DTO = "listeAdresseCourrielEmployeDTO";
    public static final String AUTHENTIFICATION = "authentificationDTO";
    
    //Form
    public static final String FORM_ASSIGNER_PROJET = "assignerProjetForm";
    public static final String SUCCES = "succes/succes";
    public static final String SUCCES_MESSAGE = "succesMessage";
    public static final Object SUCCES_ASSIGNATION = "Les employés ont été assignés avec succès";
    public static final String ERR_MESSAGE = "errMess";
    
}
