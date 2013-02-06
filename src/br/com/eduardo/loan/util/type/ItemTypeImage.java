package br.com.eduardo.loan.util.type;

import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ItemTypeImage {

	public static int typeACImage(Integer type) {
		switch (type) {
			case 1:
				return R.drawable.ic_type_cd;
			case 2:
				return R.drawable.ic_type_dvd;
			case 3:
				return R.drawable.ic_type_bluray;
			case 4:
				return R.drawable.ic_type_book;
			case 5:
				return R.drawable.ic_type_game;
			case 6:
				return R.drawable.ic_type_money;
			default:
				return R.drawable.ic_type_default;
		}
	}

	public static int typeGridImage(Integer type) {
		switch (type) {
			case 1:
				return R.drawable.ic_type_grid_cd;
			case 2:
				return R.drawable.ic_type_grid_dvd;
			case 3:
				return R.drawable.ic_type_grid_bluray;
			case 4:
				return R.drawable.ic_type_grid_book;
			case 5:
				return R.drawable.ic_type_grid_game;
			case 6:
				return R.drawable.ic_type_grid_money;
			default:
				return R.drawable.ic_type_grid_default;
		}
	}
}
