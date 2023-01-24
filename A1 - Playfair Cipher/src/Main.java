public class Main
{
    public static void main(String[] args)
    {
        String Key = "Tomatojuice";
        String PlnTxt = "Mary has a little lamb its fleece as white as snow";

        System.out.println("Key: " + Key + "\nPlainText: " + PlnTxt);

        PlayFair PlayFairCipher = new PlayFair(Key, PlnTxt);
        PlayFairCipher.removeDoubles();
        PlayFairCipher.genCipherKey();

        String encryptText = PlayFairCipher.encryptMessage();
        System.out.println("Cipher Text is: " + encryptText);
    }
}