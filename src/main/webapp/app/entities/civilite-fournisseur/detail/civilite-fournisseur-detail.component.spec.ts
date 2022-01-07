import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiviliteFournisseurDetailComponent } from './civilite-fournisseur-detail.component';

describe('CiviliteFournisseur Management Detail Component', () => {
  let comp: CiviliteFournisseurDetailComponent;
  let fixture: ComponentFixture<CiviliteFournisseurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CiviliteFournisseurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ civiliteFournisseur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CiviliteFournisseurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CiviliteFournisseurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load civiliteFournisseur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.civiliteFournisseur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
