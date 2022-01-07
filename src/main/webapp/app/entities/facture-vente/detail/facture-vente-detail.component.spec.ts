import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FactureVenteDetailComponent } from './facture-vente-detail.component';

describe('FactureVente Management Detail Component', () => {
  let comp: FactureVenteDetailComponent;
  let fixture: ComponentFixture<FactureVenteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FactureVenteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ factureVente: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FactureVenteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FactureVenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load factureVente on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.factureVente).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
