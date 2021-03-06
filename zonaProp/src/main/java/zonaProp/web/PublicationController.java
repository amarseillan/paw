package zonaProp.web;




import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import zonaProp.model.mailing.MailingService;
import zonaProp.model.photo.Photo;
import zonaProp.model.publication.Environment;
import zonaProp.model.publication.EnvironmentType;
import zonaProp.model.publication.PropertyServices;
import zonaProp.model.publication.Publication;
import zonaProp.model.publication.PublicationRepo;
import zonaProp.model.publication.ResultPage;
import zonaProp.model.user.User;
import zonaProp.model.user.UserRepo;
import zonaProp.web.command.CommentForm;
import zonaProp.web.command.EnvironmentForm;
import zonaProp.web.command.PhotoForm;
import zonaProp.web.command.PublicationForm;
import zonaProp.web.command.SearchForm;
import zonaProp.web.command.validator.CommentFormValidator;
import zonaProp.web.command.validator.EnvironmentFormValidator;
import zonaProp.web.command.validator.PhotoFormValidator;
import zonaProp.web.command.validator.PublicationFormValidator;
import zonaProp.web.command.validator.SearchFormValidator;

@Controller
public class PublicationController {

	PublicationFormValidator pfv;
	PhotoFormValidator photofv;
	CommentFormValidator cfv;
	SearchFormValidator sfv;
	EnvironmentFormValidator efv;
	PublicationRepo publications;
	UserRepo users;
	MailingService mailingService;

	@Autowired
	public PublicationController(PublicationFormValidator pfv,
			PhotoFormValidator photofv, CommentFormValidator cfv,
			SearchFormValidator sfv, EnvironmentFormValidator efv,
			PublicationRepo publications, UserRepo users,
			MailingService mailingService) {

		this.photofv = photofv;
		this.pfv = pfv;
		this.sfv = sfv;
		this.cfv = cfv;
		this.efv = efv;
		this.publications = publications;
		this.users = users;
		this.mailingService = mailingService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search() {

		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		return mav;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView searchResults(SearchForm sf, Errors errors) {
		sfv.validate(sf, errors);

		ModelAndView mav = new ModelAndView();
		
		mav.addObject("searchForm", sf);

		if (errors.hasErrors()) {
			mav.setViewName("publication/search");
			return mav;
		} else {
			ResultPage resultPage = publications.getSome(sf.build());
			mav.addObject("resultPage", resultPage);
			return mav;
		}

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("publicationId") Publication p) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("publication", p);
		mav.addObject("commentForm", new CommentForm());
		mav.addObject("searchForm", new SearchForm());
		mav.addObject("showPublisher", false);
		p.access();
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView comment(@RequestParam("publicationId") Publication p,
			CommentForm cf, Errors errors) {

		cfv.validate(cf, errors);

		ModelAndView mav = new ModelAndView();

		if (errors.hasErrors()) {
			mav.addObject("showPublisher", false);
		} else {
			mav.addObject("showPublisher", true);
			mailingService.contact(cf.build(), p.getPublisher());
		}

		mav.addObject("publication", p);
		mav.addObject("searchForm", new SearchForm());
		mav.setViewName("publication/view");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView uploadPhoto(
			@RequestParam("publicationId") Publication p, PhotoForm pF,
			Errors errors, HttpSession s) {

		User u = users.get((Integer) s.getAttribute("userId"));

		if (!p.belongsTo(u)) {
			return null;
		}
		photofv.validate(pF, errors);

		if (!errors.hasErrors()) {
			Photo image = pF.build();
			p.addPhoto(image);
			ModelAndView mav = new ModelAndView("redirect:editPhotos");
			mav.addObject("publicationId", p.getId());
			return mav;
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("publication/editPhotos");
		mav.addObject("publication", p);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editPhotos(@RequestParam("publicationId") Publication p) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("publication", p);
		mav.addObject("photoForm", new PhotoForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView deletePhoto(
			@RequestParam("publicationId") Publication p,
			@RequestParam("imageId") Photo photo, HttpSession s) {

		User u = users.get((Integer) s.getAttribute("userId"));

		ModelAndView mav = new ModelAndView();

		if (!p.belongsTo(u)) {
			return null;
		}
		p.deletePhoto(photo);
		mav.addObject("publication", p);
		mav.addObject("photoForm", new PhotoForm());
		mav.setViewName("publication/editPhotos");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam("publicationId") Publication p) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("publicationForm", new PublicationForm(p));
		mav.addObject("services", PropertyServices.values());
		mav.setViewName("publication/ABM");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("publicationForm", new PublicationForm());
		mav.addObject("services", PropertyServices.values());
		mav.setViewName("publication/ABM");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveChanges(PublicationForm pf, Errors errors,
			HttpSession s) {

		pfv.validate(pf, errors);

		ModelAndView mav = new ModelAndView();

		User publisher = users.get((Integer) s.getAttribute("userId"));

		if (errors.hasErrors()) {
			mav.addObject("publicationForm", pf);
			mav.addObject("services", PropertyServices.values());
			mav.setViewName("publication/ABM");
			return mav;
		}

		Publication p = pf.build();
		p.setPublisher(publisher);
		if (p.isNew()) {
			publications.add(p);
		} else {
			Publication old = publications.get(p.getId());
			old.update(p);
		}

		return new ModelAndView("redirect:../user/publications");

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView addEnvironment(
			@RequestParam("publicationId") Publication p) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("publication", p);
		mav.addObject("environmentForm", new EnvironmentForm());
		mav.addObject("environmentTypes", EnvironmentType.values());
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView deleteEnv(
			@RequestParam("publicationId") Publication p,
			@RequestParam("envId") Environment e, HttpSession s) {

		User u = users.get((Integer) s.getAttribute("userId"));

		if (!p.belongsTo(u)) {
			return null;
		}

		ModelAndView mav = new ModelAndView();
		p.deleteEnvironment(e);
		mav.addObject("publicationForm", new PublicationForm(p));
		mav.addObject("services", PropertyServices.values());
		mav.setViewName("publication/ABM");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView addEnvironment(
			@RequestParam("publicationId") Publication p, EnvironmentForm ef,
			Errors errors, HttpSession s) {

		User u = users.get((Integer) s.getAttribute("userId"));

		if (!p.belongsTo(u)) {
			return null;
		}
		efv.validate(ef, errors);

		if (!errors.hasErrors()) {
			Environment env = ef.build();
			p.addEnvironment(env);
			ModelAndView mav = new ModelAndView("redirect:modify");
			mav.addObject("publicationId", p.getId());
			return mav;
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("publication", p);
		mav.addObject("environmentForm", ef);
		mav.addObject("environmentTypes", EnvironmentType.values());
		return mav;

	}

}
