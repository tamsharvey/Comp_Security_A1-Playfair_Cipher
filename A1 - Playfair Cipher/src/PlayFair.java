import java.util.*;

public class PlayFair
{
    String key;
    String plnTxt;
    char[][] mtrx = new char[6][6];

    public PlayFair(String key, String plnTxt)
    {
        // Convert characters to uppercase
        this.key = key.toUpperCase();
        this.plnTxt = plnTxt.toUpperCase();

        // Checks for spaces
        boolean sapceCheck = plnTxt.contains(" ");
        System.out.println("String has spaces: " + sapceCheck);

        // Check for digits
        boolean digitCheck = false;
        char[] chars = plnTxt.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars)
            if(Character.isDigit(c))
                digitCheck = true;

        System.out.println("String contains digits: " + digitCheck);
    }

    // Remove duplicate characters from key
    public void removeDoubles()
    {
        LinkedHashSet<Character> set = new LinkedHashSet<Character>();

        String newKey = "";

        for (int i = 0; i < key.length(); i++)
            set.add(key.charAt(i));


        Iterator<Character> it = set.iterator();

        while (it.hasNext())
            newKey += (Character)it.next();

        key = newKey;
    }

    // Generate playfair cipher key table
    public void genCipherKey()
    {
        Set<Character> set = new HashSet<Character>();

        for (int i = 0; i < key.length(); i++)
        {
            if (key.charAt(i) == 'j')
                continue;
            set.add(key.charAt(i));
        }
        // remove repeated characters from the cipher key
        String tempKey = new String(key);

        // Alphabet added to set for matrix
        for (int i = 0; i < 26; i++)
        {
            char ch = (char)(i + 65);
            if (ch == 'j')
                continue;

            if (!set.contains(ch))
                tempKey += ch;
        }

        // Adding digits for matrix
        for (int i = 0; i <= 9;)
        {
            for (int j = 0; j <=8; j++)
            {
                i++;
                char ch = (char)(j + 49);
                if (ch == 'j')
                    continue;

                if (!set.contains(ch))
                    tempKey += ch;
            }

            char ch = (char)(i + 23);
            if (ch == 'j')
                continue;

            if (!set.contains(ch))
                tempKey += ch;
        }

        // Creating matrix for cipher
        for (int i = 0, idx = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                mtrx[i][j] = tempKey.charAt(idx++);

        System.out.println("Playfair Cipher Key Matrix:");

        for (int i = 0; i < 6; i++)
            System.out.println(Arrays.toString(mtrx[i]));
    }

    // Format plain text
    public String formatPlnTxt()
    {
        String message = "";
        int len = plnTxt.length();

        // Iterating through plain text and appending characters to message string
        for (int i = 0; i < len; i++)
        {
            // Replacing 0 in plain text with O
            if (plnTxt.charAt(i) == '0')
                message += 'O';
            else
                message += plnTxt.charAt(i);
        }

        // Checking length of message string
        // If length is not divisible by 2 add dummy character to the end
        if (len % 2 == 1)
            message = message + "X";

        // If letter repeated insert X
        for (int i = 0; i < len; i ++)
            if (message.charAt(i) == message.charAt(i + 1))
                message = message.substring(0, i + 1) + 'X'
                        + message.substring(i + 1);

        // Unsure whether spaces should be encoded in the solution so included a way of removing the spaces before encoding happens.
        // message = message.replaceAll(" ", "");

        System.out.println("Message with inserted 'X' :" + message);
        return message;
    }

    // Create pairs of characters
    public String[] mkPairs(String message)
    {
        int len = message.length();

        String[] pairs = new String[len / 2];

        for (int i = 0, cnt = 0; i < len / 2; i++)
            pairs[i] = message.substring(cnt, cnt += 2);

        return pairs;
    }

    // Return characters position in the table
    public int[] getCharPos(char ch)
    {
        int[] keyPos = new int[2];

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 6; j++)
            {

                if (mtrx[i][j] == ch)
                {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }

    public String encryptMessage()
    {
        String message = formatPlnTxt();
        String[] msgPairs = mkPairs(message);
        String encryptTxt = "";

        for (int i = 0; i < msgPairs.length; i++)
        {
            char char1 = msgPairs[i].charAt(0);
            char char2 = msgPairs[i].charAt(1);
            int[] char1Pos = getCharPos(char1);
            int[] char2Pos = getCharPos(char2);

            // If both characters in same row
            if (char1Pos[0] == char2Pos[0])
            {
                char1Pos[1] = (char1Pos[1] + 1) % 6;
                char2Pos[1] = (char2Pos[1] + 1) % 6;
            }

            // If both characters in same column
            else if (char1Pos[1] == char2Pos[1])
            {
                char1Pos[0] = (char1Pos[0] + 1) % 6;
                char2Pos[0] = (char2Pos[0] + 1) % 6;
            }

            // If characters in different rows and columns
            else
            {
                int temp = char1Pos[1];
                char1Pos[1] = char2Pos[1];
                char2Pos[1] = temp;
            }

            // Get corresponding cipher characters from key matrix
            encryptTxt = encryptTxt + mtrx[char1Pos[0]][char1Pos[1]]
                    + mtrx[char2Pos[0]][char2Pos[1]];
        }
        return encryptTxt;
    }
}




