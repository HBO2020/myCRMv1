import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContactFournisseurDetailComponent } from './contact-fournisseur-detail.component';

describe('ContactFournisseur Management Detail Component', () => {
  let comp: ContactFournisseurDetailComponent;
  let fixture: ComponentFixture<ContactFournisseurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactFournisseurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contactFournisseur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ContactFournisseurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContactFournisseurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contactFournisseur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contactFournisseur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
