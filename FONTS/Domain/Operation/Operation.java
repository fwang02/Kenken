/**
 * @author Javier Parcerisas Nisa
 */
package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;


public abstract class Operation {

	public abstract int checkResult(Kenken kk, KenkenCage kkc);

	public abstract char getChar();

}