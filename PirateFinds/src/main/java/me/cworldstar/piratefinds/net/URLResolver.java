package me.cworldstar.piratefinds.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class URLResolver {

	/**
	 * 
	 * The {@link #createURL} method of {@link URLResolver} returns an
	 * {@link Optional} of a {@link URL}. Will automatically catch errors.
	 * Is nullable.
	 * 
	 * @param url
	 * @return An {@link Optional} of a {@link URL}.
	 */
	@Nullable
	public static Optional<URL> createURL(@Nonnull final String url) {
		
		URL created = null;
		
		try {
			created = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(created);
	}
	
}
