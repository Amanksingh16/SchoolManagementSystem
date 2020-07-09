package kmsg.sms.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;


public class SHAHandler {

	  private static final Random RANDOM = new SecureRandom();
	  private static final int ITERATIONS = 10000;
	  private static final int KEY_LENGTH = 256;

	  /**
	   * static utility class
	   */
	  private SHAHandler() { }

	  public static final int PASSWORD_LENGTH = 10;
	  
		static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		public static int generateOTP() 
		{
			final int MAX = 99999; // four digit number
			final int MIN = 10000; // four digit number
			// Random OTP Generation
			final Random RANDOM = new SecureRandom();
			return RANDOM.nextInt( MAX - MIN + 1 ) + MIN;
		}
		/**
		   * Returns a random salt to be used to hash a password.
		   *
		   * @return a 16 bytes random salt
		   */
	  public static byte[] getNextSalt() {
	    byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return salt;
	  }

	  /**
	   * Returns a salted and hashed password using the provided hash.<br>
	   * Note - side effect: the password is destroyed (the char[] is filled with zeros)
	   *
	   * @param password the password to be hashed
	   * @param salt     a 16 bytes salt, ideally obtained with the getNextSalt method
	   *
	   * @return the hashed password with a pinch of salt
	   */
	  
	  public static String generateHash(String input) {
			StringBuilder hash = new StringBuilder();

			try {
				MessageDigest sha = MessageDigest.getInstance("SHA-1");
				byte[] hashedBytes = sha.digest(input.getBytes());
				char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
						'a', 'b', 'c', 'd', 'e', 'f' };
				for (int idx = 0; idx < hashedBytes.length; ++idx) {
					byte b = hashedBytes[idx];
					hash.append(digits[(b & 0xf0) >> 4]);
					hash.append(digits[b & 0x0f]);
				}
			} catch (NoSuchAlgorithmException e) {
				// handle error here.
			}

			return hash.toString();
		}

	  public static byte[] hash(char[] password, byte[] salt) {
	    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
	    Arrays.fill(password, Character.MIN_VALUE);
	    try {
	      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	      return skf.generateSecret(spec).getEncoded();
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
	    } finally {
	      spec.clearPassword();
	    }
	  }

	  /**
	   * Returns true if the given password and salt match the hashed value, false otherwise.<br>
	   * Note - side effect: the password is destroyed (the char[] is filled with zeros)
	   *
	   * @param password     the password to check
	   * @param salt         the salt used to hash the password
	   * @param expectedHash the expected hashed value of the password
	   *
	   * @return true if the given password and salt match the hashed value, false otherwise
	   */
	  public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
	    byte[] pwdHash = hash(password, salt);
	    Arrays.fill(password, Character.MIN_VALUE);
	    if (pwdHash.length != expectedHash.length) return false;
	    for (int i = 0; i < pwdHash.length; i++) {
	      if (pwdHash[i] != expectedHash[i]) return false;
	    }
	    return true;
	  }

	  /**
	   * Generates a random password of a given length, using letters and digits.
	   *
	   * @param length the length of the password
	   *
	   * @return a random password
	   */
	  public static String generateRandomPassword(int length) {
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	      int c = RANDOM.nextInt(62);
	      if (c <= 9) {
	        sb.append(String.valueOf(c));
	      } else if (c < 36) {
	        sb.append((char) ('a' + c - 10));
	      } else {
	        sb.append((char) ('A' + c - 36));
	      }
	    }
	    return sb.toString();
	  }
	  
	  public static void main(String[] args) {
		String password = "Chandra@123";
		String salt = SHAHandler.getNextSalt().toString();
		String securePass = SHAHandler.generateHash(password + salt);
		System.out.println("securePass    "+securePass);
		System.out.println("salt"+salt);
	}
	}