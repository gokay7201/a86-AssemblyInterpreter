// mov da bi �zelli�i implemente ederken �nce ax'e ediyorum.
// de�i�ik inputlarla testen sonra ba�ar�l� 
// olursa di�erlierine copy paste basit zaten
//**
//immeadite, register yap�ld� gibi
//register indirect ve memory yap�yorum
//stack addresssing tam ne bilmiyoum, ��reniyim bak�cam
// mov dl,"*" diye bi�i varm��, asciiden hexaya �evirip yaz�yo. yeni ��rendim bak�cam
//**

public class Hyp86 {

	// byte arrayi yap�nca char� koyamay�z o y�zden array boyutunu yar�ya d���rd�m.
	// bu 2^15 yap�yo. hoca da bu uzunlukta tutmu�tu zaten
	// ama fffe 65534 yap�yo
	// bence biz arrayi yine 64k tutmal�y�z
	char[] memory = new char[32768 * 2]; // hexa tutal�m

	// bu 4 � ne ise yar�yo bilmiyorum henuz
	char[] di = new char[4];
	char[] sp = new char[4];
	char[] si = new char[4];
	char[] bp = new char[4];

	char[] ax = new char[4]; // ah:al seklinde tutuyoruz. ilk 2 indis ah, son 2 indis al
	char[] bx = new char[4];
	char[] cx = new char[4];
	char[] dx = new char[4];

	boolean ZF = false;
	boolean AF = false;
	boolean CF = false;
	boolean OF = false;

	int numberOfInstructions;
	String SP = "FFFE"; // stack pointer , memoryye erisirken hexadan decimale cevircez
	int MP = 0; // memory Pointer, instructionlar� okuduktan sonra 6*n' e kadar -1 falan yap�p
				// eri�ilmez k�lmam�z laz�m. (n=number of instructions)

	Hyp86(String s) { // constructor
		numberOfInstructions = 0; // bunu initialize edip ona g�re memory ay�rcaz
	}

	public static void add(String first, String second) {

	}

	// !!!!!!!!!!!!! mov:
	// second offset i�erebilir veya var i�eribilir
	// When using variable names offset variable-name accesses the address,
	// just the variable-name accesses the value.
	// !!!!!!!!!!!!!

	// firstin memorydei adresine second�n de�erini ta��
	// second pointer olabilir ex: [454]
	// second say� olabilir ex: 454
	// second reg olabilir ex: cl
	// first reg veya memory
	// second boyutu firstten b�y�kse hata ver

	public void mov_mem_xx(String first, String second) {

	}

	public void mov_ax_unknown(String second) {
		if (second.contains("[") && second.contains("]")) { // second is memory
			// w[xxxx] ya da b[xxxx] olabilir
			// [bx] olabilir
			// w[xxxxh] ya da b[0xxxxh] olabilir. fuck uzun s�r�cek!!!

			if (second.charAt(0) == 'b') { // tek byte
				second = second.substring(2, second.length() - 1); // got rid of b[ and ]

			} else if (second.contains("ax") || second.contains("cx") || second.contains("bx") || second.contains("dx")
					|| second.contains("al") || second.contains("ah") || second.contains("bl") || second.contains("bh")
					|| second.contains("cl") || second.contains("ch") || second.contains("dl")
					|| second.contains("dh")) { // register indirect : mov ax,[bx] -> burda bazen bad index register
												// yiyebiliyoruz nedennii ��zemedim.
												// belki memoryde �yle bi adres olmad��� i�indir.
				second = second.substring(1, second.length() - 1); // got rid of [ and ]
				String num = "";
				if (second.equals("ax")) {
					for (int i = 0; i <= 3; i++)
						num += ax[i];
				} else if (second.equals("bx")) {
					for (int i = 0; i <= 3; i++)
						num += bx[i];
				} else if (second.equals("cx")) {
					for (int i = 0; i <= 3; i++)
						num += cx[i];
				} else if (second.equals("dx")) {
					for (int i = 0; i <= 3; i++)
						num += dx[i];
				} else if (second.equals("al")) {
					for (int i = 0; i <= 1; i++)
						num += ax[i + 2];
				} else if (second.equals("ah")) {
					for (int i = 0; i <= 1; i++)
						num += ax[i];
				} else if (second.equals("bl")) {
					for (int i = 0; i <= 1; i++)
						num += bx[i + 2];
				} else if (second.equals("bh")) {
					for (int i = 0; i <= 1; i++)
						num += bx[i];
				} else if (second.equals("cl")) {
					for (int i = 0; i <= 1; i++)
						num += cx[i + 2];
				} else if (second.equals("ch")) {
					for (int i = 0; i <= 1; i++)
						num += cx[i];
				} else if (second.equals("dl")) {
					for (int i = 0; i <= 1; i++)
						num += dx[i + 2];
				} else if (second.equals("dh")) {
					for (int i = 0; i <= 1; i++)
						num += dx[i];
				}

				// [register] adresi al�nd� �imdi memory s�n�rlar� i�inde mi ona bak�caz
				// �yleyse memorydeki o adresi kopyala de�ilse hata ver kapat
				if (Integer.parseInt(num, 16) >= 32768 && Integer.parseInt(num, 16) <= numberOfInstructions * 6) {
					System.err.println("Bad memory address");
					System.exit(0);
				} else {

					// !??!!?!?!?!??!?!
					for (int i = 0; i <= 3; i++) { // memoryde de 1er 1er art�rcaz m�?? yoksa direk o indisin i�indeki
													// say�y� m� at�caz??
						// ax[3-i]=memory[Integer.parseInt(num, 16)+i];
					}
				}
			} else {// w kabul et: mov ax, w[xxxx] ya da mov ax, [xxxx] ya da mov ax,[xxxxh] ya da
					// mov ax,[0xxxxh]
				if (second.charAt(0) == 'w') {
					second = second.substring(2, second.length() - 1); // got rid of w[ and ]
					// to be continued
				} else {
					second = second.substring(1, second.length() - 1); // got rid of [ and ]
					// to be continued
				}

			}

		} else if (second.equals("bx")) {
			for (int i = 3; i >= 0; i--) {
				ax[i] = bx[i];
			}
		} else if (second.equals("cx")) {
			for (int i = 3; i >= 0; i--) {
				ax[i] = cx[i];
			}
		} else if (second.equals("dx")) {
			for (int i = 3; i >= 0; i--) {
				ax[i] = dx[i];
			}
		} else if (second.contains("al") || second.contains("ah") || second.contains("bl") || second.contains("bh")
				|| second.contains("cl") || second.contains("ch") || second.contains("dl") || second.contains("dh")) {// error
			System.out.println("Error: Byte/Word Combination Not Allowed");
			System.exit(0);
		}
		// bu k�s�m ok gibi ama de�i�ik inputlarla test etmek laz�m pek emin de�ilim
		else {// var olanilir, "offset var" olabilir

			// 01h olabilir -> h yi at�p yerle�tir +, 5d olabilir -> dyi at�p hexaya �evir
			// +,
			// 132 olabilir->hexaya �evir+, 0'la ba�l�yosa hexa al�yo direk

			// !!!! 0534d yi decimal okumuyo 534d diye hexa okuyo +.
			if (second.charAt(second.length() - 1) == 'd' && second.charAt(0) != '0') {
				second = second.substring(0, second.length() - 1); // got rid of d
				second = Integer.toHexString(Integer.valueOf(second));// decimal turned to hexa
			} else {
				if (second.charAt(second.length() - 1) == 'h') {
					second = second.substring(0, second.length() - 1); // got rid of h
				}
			}
			// finally second is hexa and ready to be inserted into register
			// reset register
			for (int i = 0; i < 3; i++)
				ax[i] = 0;
			for (int i = 0; i <= 3 && i < second.length(); i++) {
				ax[3 - i] = second.charAt(second.length() - i - 1);
			}
		}

	}

	public void mov_bx_unknown(String second) {
		if (second.contains("[") && second.contains("]")) { // second is memory

		} else if (second.contains("ax")) {
			for (int i = 3; i >= 0; i--) {
				bx[i] = ax[i];
			}
		} else if (second.contains("cx")) {
			for (int i = 3; i >= 0; i--) {
				bx[i] = cx[i];
			}
		} else if (second.contains("dx")) {
			for (int i = 3; i >= 0; i--) {
				bx[i] = dx[i];
			}
		} else if (second.contains("al") || second.contains("ah") || second.contains("bl") || second.contains("bh")
				|| second.contains("cl") || second.contains("ch") || second.contains("dl") || second.contains("dh")) {// error
			System.out.println("Error: Byte/Word Combination Not Allowed");
			System.exit(0);
		} else {// number

		}

	}

	public void mov_cx_unknown(String second) {
		if (second.contains("[") && second.contains("]")) { // second is memory

		} else if (second.contains("ax")) {
			for (int i = 3; i >= 0; i--) {
				cx[i] = ax[i];
			}
		} else if (second.contains("bx")) {
			for (int i = 3; i >= 0; i--) {
				cx[i] = bx[i];
			}
		} else if (second.contains("dx")) {
			for (int i = 3; i >= 0; i--) {
				cx[i] = dx[i];
			}
		} else if (second.contains("al") || second.contains("ah") || second.contains("bl") || second.contains("bh")
				|| second.contains("cl") || second.contains("ch") || second.contains("dl") || second.contains("dh")) {// error
			System.out.println("Error: Byte/Word Combination Not Allowed");
			System.exit(0);
		} else {// number

		}

	}

	public void mov_dx_unknown(String second) {
		if (second.contains("[") && second.contains("]")) { // second is memory

		} else if (second.contains("ax")) {
			for (int i = 3; i >= 0; i--) {
				dx[i] = ax[i];
			}
		} else if (second.contains("bx")) {
			for (int i = 3; i >= 0; i--) {
				dx[i] = bx[i];
			}
		} else if (second.contains("cx")) {
			for (int i = 3; i >= 0; i--) {
				dx[i] = cx[i];
			}
		} else if (second.contains("al") || second.contains("ah") || second.contains("bl") || second.contains("bh")
				|| second.contains("cl") || second.contains("ch") || second.contains("dl") || second.contains("dh")) {// error
			System.out.println("Error: Byte/Word Combination Not Allowed");
			System.exit(0);
		} else {// number
		}
	}

	public void mov_al_unknown(String second) {
		if (second.equals("ah")) {
			for (int i = 3; i >= 2; i--) {
				ax[i] = ax[i - 2];
			}
		} else if (second.equals("bl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i] = bx[i];
			}
		} else if (second.equals("bh")) {
			for (int i = 3; i >= 2; i--) {
				ax[i] = bx[i - 2];
			}
		} else if (second.equals("cl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i] = cx[i];
			}
		} else if (second.equals("ch")) {
			for (int i = 3; i >= 2; i--) {
				ax[i] = cx[i - 2];
			}
		} else if (second.equals("dl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i] = dx[i];
			}
		} else if (second.equals("dh")) {
			for (int i = 3; i >= 2; i--) {
				ax[i] = dx[i - 2];
			}
		} else if (second.contains("x")) {
			System.out.println("Byte/Word Combination Not Allowed");
			System.exit(0);
		} else { // �u diger regler olab�l�r sonra yap�cam

		}
	}

	public void mov_ah_unknown(String second) {
		if (second.equals("al")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = ax[i];
			}
		} else if (second.equals("bl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = bx[i];
			}
		} else if (second.equals("bh")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = bx[i - 2];
			}
		} else if (second.equals("cl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = cx[i];
			}
		} else if (second.equals("ch")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = cx[i - 2];
			}
		} else if (second.equals("dl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = dx[i];
			}
		} else if (second.equals("dh")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = dx[i - 2];
			}
		} else if (second.contains("x")) {
			System.out.println("Byte/Word Combination Not Allowed");
			System.exit(0);
		} else { // �u diger regler olab�l�r sonra yap�cam

		}
	}

	public void mov_bl_unknown(String second) {
		if (second.equals("al")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = ax[i];
			}
		} else if (second.equals("bl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = bx[i];
			}
		} else if (second.equals("bh")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = bx[i - 2];
			}
		} else if (second.equals("cl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = cx[i];
			}
		} else if (second.equals("ch")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = cx[i - 2];
			}
		} else if (second.equals("dl")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = dx[i];
			}
		} else if (second.equals("dh")) {
			for (int i = 3; i >= 2; i--) {
				ax[i - 2] = dx[i - 2];
			}
		} else if (second.contains("x")) {
			System.out.println("Byte/Word Combination Not Allowed");
			System.exit(0);
		} else { // �u diger regler olab�l�r sonra yap�cam
		}
	}

	public void mov_bh_unknown(String second) {

	}

	public void mov_cl_unknown(String second) {
		if (second.equals("al")) {
			for (int i = 3; i >= 2; i--) {
				cx[i] = ax[i];
			}
		} else if (second.equals("ah")) {
			for (int i = 3; i >= 2; i--) {
				cx[i] = ax[i - 2];
			}
		} else if (second.equals("bl")) {
			for (int i = 3; i >= 2; i--) {
				cx[i] = bx[i];
			}
		} else if (second.equals("bh")) {
			for (int i = 3; i >= 2; i--) {
				cx[i] = bx[i - 2];
			}
		} else if (second.equals("ch")) {
			for (int i = 3; i >= 2; i--) {
				cx[i] = cx[i - 2];
			}
		} else if (second.equals("dl")) {
			for (int i = 3; i >= 2; i--) {
				cx[i] = dx[i];
			}
		} else if (second.equals("dh")) {
			for (int i = 3; i >= 2; i--) {
				cx[i] = dx[i - 2];
			}
		} else if (second.contains("x")) {
			System.out.println("Byte/Word Combination Not Allowed");
			System.exit(0);
		} else { // �u diger regler olab�l�r sonra yap�cam

		}
	}

	public void mov_ch_unknown(String second) {
		if (second.equals("al")) {
			for (int i = 3; i >= 2; i--) {
				cx[i - 2] = ax[i];
			}
		} else if (second.equals("ah")) {
			for (int i = 3; i >= 2; i--) {
				cx[i - 2] = ax[i - 2];
			}
		} else if (second.equals("bl")) {
			for (int i = 3; i >= 2; i--) {
				cx[i - 2] = bx[i];
			}
		} else if (second.equals("bh")) {
			for (int i = 3; i >= 2; i--) {
				cx[i - 2] = bx[i - 2];
			}
		} else if (second.equals("cl")) {
			for (int i = 3; i >= 2; i--) {
				cx[i - 2] = cx[i];
			}
		} else if (second.equals("dl")) {
			for (int i = 3; i >= 2; i--) {
				cx[i - 2] = dx[i];
			}
		} else if (second.equals("dh")) {
			for (int i = 3; i >= 2; i--) {
				cx[i - 2] = dx[i - 2];
			}
		} else if (second.contains("x")) {
			System.out.println("Byte/Word Combination Not Allowed");
			System.exit(0);
		} else { // �u diger regler olab�l�r sonra yap�cam
		}
	}

	public void mov_dl_unknown(String second) {
		if (second.equals("al")) {
			for (int i = 3; i >= 2; i--) {
				dx[i] = ax[i];
			}
		} else if (second.equals("ah")) {
			for (int i = 3; i >= 2; i--) {
				dx[i] = ax[i - 2];
			}
		} else if (second.equals("bl")) {
			for (int i = 3; i >= 2; i--) {
				dx[i] = bx[i];
			}
		} else if (second.equals("bh")) {
			for (int i = 3; i >= 2; i--) {
				dx[i] = bx[i - 2];
			}
		} else if (second.equals("cl")) {
			for (int i = 3; i >= 2; i--) {
				dx[i] = cx[i];
			}
		} else if (second.equals("ch")) {
			for (int i = 3; i >= 2; i--) {
				dx[i] = cx[i - 2];
			}
		} else if (second.equals("dh")) {
			for (int i = 3; i >= 2; i--) {
				dx[i] = dx[i - 2];
			}
		} else if (second.contains("x")) {
			System.out.println("Byte/Word Combination Not Allowed");
			System.exit(0);
		} else { // �u diger regler olab�l�r sonra yap�cam

		}
	}

	public void mov_dh_unknown(String second) {
		if (second.equals("al")) {
			for (int i = 3; i >= 2; i--) {
				dx[i - 2] = ax[i];
			}
		} else if (second.equals("ah")) {
			for (int i = 3; i >= 2; i--) {
				dx[i - 2] = ax[i - 2];
			}
		} else if (second.equals("bl")) {
			for (int i = 3; i >= 2; i--) {
				dx[i - 2] = bx[i];
			}
		} else if (second.equals("bh")) {
			for (int i = 3; i >= 2; i--) {
				dx[i - 2] = bx[i - 2];
			}
		} else if (second.equals("cl")) {
			for (int i = 3; i >= 2; i--) {
				dx[i - 2] = cx[i];
			}
		} else if (second.equals("ch")) {
			for (int i = 3; i >= 2; i--) {
				dx[i - 2] = cx[i - 2];
			}
		} else if (second.equals("dl")) {
			for (int i = 3; i >= 2; i--) {
				dx[i - 2] = dx[i];
			}
		} else if (second.contains("x")) {
			System.out.println("Byte/Word Combination Not Allowed");
			System.exit(0);
		} else { // �u diger regler olab�l�r sonra yap�cam
		}
	}

	public void mov_reg_unknown(String first, String second) {

		if (first.equalsIgnoreCase("ax")) { // mov *x,*l ya da *x, *h hatas� yap�ld�
			mov_ax_unknown(second);
		} else if (first.equalsIgnoreCase("bx")) {
			mov_bx_unknown(second);
		} else if (first.equalsIgnoreCase("cx")) {
			mov_cx_unknown(second);
		} else if (first.equalsIgnoreCase("dx")) {
			mov_dx_unknown(second);
		} else if (first.equalsIgnoreCase("al")) {
			mov_al_unknown(second);
		} else if (first.equalsIgnoreCase("ah")) {
			mov_ah_unknown(second);
		} else if (first.equalsIgnoreCase("bl")) {
			mov_bl_unknown(second);
		} else if (first.equalsIgnoreCase("bh")) {
			mov_bh_unknown(second);
		} else if (first.equalsIgnoreCase("cl")) {
			mov_cl_unknown(second);
		} else if (first.equalsIgnoreCase("ch")) {
			mov_ch_unknown(second);
		} else if (first.equalsIgnoreCase("dl")) {
			mov_dl_unknown(second);
		} else if (first.equalsIgnoreCase("dh")) {
			mov_dh_unknown(second);
		}
	}

	public void mov(String first, String second) {

		if (first.contains("[") && first.contains("]")) { // first is memory

			mov_mem_xx(first, second);

		} else if (first.equals("ax") || first.equals("cx") || first.equals("bx") || first.equals("dx")
				|| first.equals("ah") || first.equals("al") || first.equals("bl") || first.equals("bh")
				|| first.equals("ch") || first.equals("cl") || first.equals("dl") || first.equals("dh")) { // first is
			// reg
			mov_reg_unknown(first, second);

		} else { // reg veya memoryye yazm�yo hata ver
			System.out.println("Undefined sysbols are listed");
			System.exit(0);
		}

	}

	public int hexaToDecimal(String s) {
		return Integer.parseInt(s, 16);
	}

	public String DecimalToHexa(int a) {
		return Integer.toHexString(a);
	}

	public int binaryToDecimal(int a) {
		return Integer.parseInt("+a+", 2);
	}

	public static int DecimalToBinary(int a) {
		return Integer.valueOf(Integer.toBinaryString(a).substring(0, Integer.toBinaryString(a).length()));
	}

}
