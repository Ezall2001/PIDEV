package tests;

import java.io.IOException;

import api.Mailing;
import entities.User;
import profanityUtility.Profinity;
import utils.Log;

public class Armen_test {

  public static void main(String[] args) throws IOException {
    Mailing.send_mail("armen.bakir@esprit.tn", "sujet",
        "dfqsdfqsdfgqsdkjdsuhdhjsufllqsdiqdisuuhhffslqdimqdilsmuuffhhggsdsiilmm");

  }

}
