package pl.polsl.lab.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
/**
 * Service class which checks palindrome or create a new one.
 * 
 * @author Dawid Kampa  
 * @version 2.1
 */

public class PalindromeService {
    
    /**
     * Method is checikng if palindrome is correct
     * 
     * @param palindrome given palindrome
     * @return true if given value is palindrome
     */
    public boolean checkPalindrome(String palindrome) {
        /** given argument is converted to Array of char. */
        List<Character> palindromeChar = new ArrayList<>();
        //char[] palindromeChar;     
        
        for(char c : palindrome.toCharArray() ){
        palindromeChar.add(c);
        }
       // palindromeChar = String.valueOf(palindrome).toCharArray();
        /** loop which compere first and last element, 
         * next approximate to midlle of array
         */
        for(int i = 0; i < palindromeChar.size() / 2; i++) {
            if(!Objects.equals(palindromeChar.get(i), palindromeChar.get(palindromeChar.size() - i - 1))) {
            return false;
            }
        }
        return true; 
    }
    /**
     *  Method is checikng if any number of palindromes is correct
     * 
     * @param palindromes given palindromes
     * @return positions of correct palindromes
     */
    public String checkPalindromes(String...palindromes)  {
        
        StringBuilder positionOfCorrectPalindrome = new StringBuilder(); 
        
        for(int i = 0 ; i < palindromes.length; i++){
            if(checkPalindrome(palindromes[i])){
             positionOfCorrectPalindrome.append((i));
             positionOfCorrectPalindrome.append(" ");
            }
        }
        if(positionOfCorrectPalindrome.toString().isEmpty()) {
            return "none";
        }
        return positionOfCorrectPalindrome.toString();
    }
        /**
         * Method creates palindrome of random length which contains number in range from first argument to second argument
         * 
         * @param firstOfRange  first of range
         * @param lastOfRange  last of range
         * @return new created palindrome
         * @throws RangeException 
         */
    
    public String generatePalindrome(int firstOfRange, int lastOfRange) 
            throws RangeException{
        if(firstOfRange > lastOfRange){//if first number of range is bigger than last
            throw new RangeException("First number of range can not be bigger than last");
        }else if(firstOfRange < 0 ){//if first number is negative
            throw new RangeException("Numberes of range can not be smaller than 0");
        }else if(lastOfRange > 9 ) {// if number is not a digit
            throw new RangeException("Numbers of range can not be bigger than 9");
        }
        //range of Array needed to create palindrome
        Random randomRange = new Random();
        
        int lengthOfPalindrome = randomRange.nextInt(100);
        StringBuilder palindromeString = new StringBuilder(lengthOfPalindrome);
        //String[] palindromeString = new String[rangeOfArray];
        Random randomElement = new Random();
        Integer elementOfPalindrome;
         /** loop which fil first and last element of array, 
         * next approximate to midlle of array 
         */
        for(int i = 0; i < lengthOfPalindrome/2; i++ ) {
            elementOfPalindrome = randomElement.nextInt(lastOfRange - firstOfRange + 1) + firstOfRange;
            palindromeString.insert(i , elementOfPalindrome);
            palindromeString.insert(i , elementOfPalindrome);  
        }
        if(lengthOfPalindrome % 2 == 1){
            elementOfPalindrome = randomElement.nextInt(lastOfRange - firstOfRange) + firstOfRange;
            palindromeString.insert(lengthOfPalindrome / 2, elementOfPalindrome);
        }
        
        
    return palindromeString.toString();
    }
    
    /**
     * Method creates palindrome of given length which contains number in range from first argument to second argument.
     * 
     * @param firstOfRange first of range 
     * @param lastOfRange  last of range
     * @param lengthOfPalindrome length of palindrome which will be created 
     * @return new created palindrome
     * @throws RangeException
     * @throws pl.polsl.lab.service.LengthException
     */
    public String generatePalindrome(int firstOfRange, int lastOfRange, int lengthOfPalindrome) 
            throws RangeException, LengthException {
        if(firstOfRange > lastOfRange){//if first number of range is bigger than last
            throw new RangeException("First number can not be bigger than last");
        }else if(firstOfRange < 0){//if first number is negative
            throw new RangeException("Number can not be smaller than 0");
        }else if(lastOfRange > 9) {// if number is not a digit
            throw new RangeException("Number can not be bigger than 9");
        }
        
        if(lengthOfPalindrome == 0) {
            throw new LengthException("Length can not be 0");
        }else if(lengthOfPalindrome < 0){
            throw new LengthException("Length can not be negative");
        }
        
        StringBuilder palindromeString = new StringBuilder(lengthOfPalindrome);
        Integer elementOfPalindrome;
        
        if(firstOfRange==lastOfRange){
            for(int i = 0; i < lengthOfPalindrome  ; i++ ) {
              elementOfPalindrome = firstOfRange;
                palindromeString.insert(i , elementOfPalindrome);
            }
        }
        else{
            Random randomElement = new Random();

            for(int i = 0; i < lengthOfPalindrome / 2 ; i++ ) {
                elementOfPalindrome = randomElement.nextInt(lastOfRange - firstOfRange + 1) + firstOfRange;
                palindromeString.insert(i , elementOfPalindrome);
                palindromeString.insert(i , elementOfPalindrome);  
            }
            
            if(lengthOfPalindrome % 2 == 1){
                elementOfPalindrome = randomElement.nextInt(lastOfRange - firstOfRange) + firstOfRange;
                palindromeString.insert(lengthOfPalindrome / 2, elementOfPalindrome);
            }
        }

    return palindromeString.toString();
    }
}
